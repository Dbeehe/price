package com.icia.price.repository;

import com.icia.price.entity.BoardEntity;
import com.icia.price.entity.CommentEntity;
import com.icia.price.entity.LikeEntity;
import com.icia.price.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByMemberEntityAndCommentEntity(MemberEntity memberEntity, CommentEntity commentEntity);
    List<LikeEntity> findByMemberEntityAndBoardEntity(MemberEntity memberEntity, BoardEntity boardEntity);
    void deleteByMemberEntityAndCommentEntity(MemberEntity memberEntity, CommentEntity commentEntity);
}