package com.icia.price.service;

import com.icia.price.dto.BoardDTO;
import com.icia.price.entity.BoardEntity;
import com.icia.price.entity.BoardFileEntity;
import com.icia.price.entity.MemberEntity;
import com.icia.price.repository.BoardFileRepository;
import com.icia.price.repository.BoardRepository;
import com.icia.price.repository.MemberRepository;
import com.icia.price.util.UtilClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;
    private final MemberRepository memberRepository;

    public Long save(BoardDTO boardDTO) throws IOException {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(boardDTO.getBoardWriter()).orElseThrow(() -> new NoSuchElementException());
        if (boardDTO.getBoardFile().get(0).isEmpty()) {
            BoardEntity boardEntity = BoardEntity.toSaveEntity(memberEntity, boardDTO);
            return boardRepository.save(boardEntity).getId();
        } else {
            BoardEntity boardEntity = BoardEntity.toSaveEntityWithFile(memberEntity, boardDTO);
            BoardEntity savedEntity = boardRepository.save(boardEntity);
            for (MultipartFile boardFile: boardDTO.getBoardFile()) {
                String originalFilename = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
                String savePath = "D:\\springboot_img\\" + storedFileName;
                boardFile.transferTo(new File(savePath));
                BoardFileEntity boardFileEntity =
                        BoardFileEntity.toSaveBoardFile(savedEntity, originalFilename, storedFileName);
                boardFileRepository.save(boardFileEntity);
            }
            return savedEntity.getId();
        }
    }

    public Page<BoardDTO> findAll(int page, String type, String q) {
        page = page - 1;
        int pageLimit = 5;
        Page<BoardEntity> boardEntities = null;
        if (q.equals("")) {
            boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else {
            if (type.equals("boardTitle")) {
                boardEntities = boardRepository.findByBoardTitleContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            } else if (type.equals("boardWriter")) {
                boardEntities = boardRepository.findByBoardWriterContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            }
        }
        Page<BoardDTO> boardList = boardEntities.map(boardEntity ->
                BoardDTO.builder()
                        .id(boardEntity.getId())
                        .boardTitle(boardEntity.getBoardTitle())
                        .boardWriter(boardEntity.getBoardWriter())
                        .boardHits(boardEntity.getBoardHits())
                        .createdAt(UtilClass.dateTimeFormat(boardEntity.getCreatedAt()))
                        .build());
        return boardList;
    }

    @Transactional
    public void increaseHits(Long id) {
        boardRepository.increaseHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return BoardDTO.toDTO(boardEntity);
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public void update(BoardDTO boardDTO) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(boardDTO.getBoardWriter()).orElseThrow(() -> new NoSuchElementException());
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(memberEntity, boardDTO);
        boardRepository.save(boardEntity);
    }
}