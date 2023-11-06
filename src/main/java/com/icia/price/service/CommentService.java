package com.icia.price.service;

import com.icia.price.dto.CommentDTO;
import com.icia.price.dto.LikeDTO;
import com.icia.price.entity.BoardEntity;
import com.icia.price.entity.CommentEntity;
import com.icia.price.entity.LikeEntity;
import com.icia.price.entity.MemberEntity;
import com.icia.price.repository.BoardRepository;
import com.icia.price.repository.CommentRepository;
import com.icia.price.repository.LikeRepository;
import com.icia.price.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final LikeRepository likeRepository;

    public Long save(CommentDTO commentDTO) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(commentDTO.getCommentWriter()).orElseThrow(() -> new NoSuchElementException());
        BoardEntity boardEntity = boardRepository.findById(commentDTO.getBoardId()).orElseThrow(() -> new NoSuchElementException());
        CommentEntity commentEntity = CommentEntity.toSaveEntity(memberEntity, boardEntity, commentDTO);
        return commentRepository.save(commentEntity).getId();
    }


    @Transactional
    public List<CommentDTO> findAll(Long memberId, Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException());

        List<CommentEntity> commentEntityList = commentRepository.findByBoardEntityOrderByIdDesc(boardEntity);

        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException());

        List<LikeEntity> likeEntityList = likeRepository.findByMemberEntityAndBoardEntity(memberEntity, boardEntity);

        List<CommentDTO> commentDTOList = new ArrayList<>();
        commentEntityList.forEach(comment -> {
            commentDTOList.add(CommentDTO.toDTO(comment, likeEntityList));
        });
        return commentDTOList;
    }

    public boolean likeCheck(LikeDTO likeDTO) {
        MemberEntity memberEntity = memberRepository.findById(likeDTO.getMemberId()).orElseThrow(() -> new NoSuchElementException());
        CommentEntity commentEntity = commentRepository.findById(likeDTO.getCommentId()).orElseThrow(() -> new NoSuchElementException());
        Optional<LikeEntity> optionalLikeEntity = likeRepository.findByMemberEntityAndCommentEntity(memberEntity, commentEntity);
        if (optionalLikeEntity.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public Long like(LikeDTO likeDTO) {
        MemberEntity memberEntity = memberRepository.findById(likeDTO.getMemberId()).orElseThrow(() -> new NoSuchElementException());
        BoardEntity boardEntity = boardRepository.findById(likeDTO.getBoardId()).orElseThrow(() -> new NoSuchElementException());
        CommentEntity commentEntity = commentRepository.findById(likeDTO.getCommentId()).orElseThrow(() -> new NoSuchElementException());
        LikeEntity likeEntity = LikeEntity.toLikeEntity(memberEntity, boardEntity, commentEntity);
        return likeRepository.save(likeEntity).getId();
    }

    @Transactional
    public void unLike(LikeDTO likeDTO) {
        MemberEntity memberEntity = memberRepository.findById(likeDTO.getMemberId()).orElseThrow(() -> new NoSuchElementException());
        CommentEntity commentEntity = commentRepository.findById(likeDTO.getCommentId()).orElseThrow(() -> new NoSuchElementException());
        likeRepository.deleteByMemberEntityAndCommentEntity(memberEntity, commentEntity);
    }
}