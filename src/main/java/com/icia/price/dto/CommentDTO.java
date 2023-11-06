package com.icia.price.dto;

import com.icia.price.entity.CommentEntity;
import com.icia.price.entity.LikeEntity;
import com.icia.price.util.UtilClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private String createdAt;
    private String updatedAt;
    private int isLike = 0;
    private int likeCount;


    public static CommentDTO toDTO(CommentEntity commentEntity, List<LikeEntity> likeEntityList) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setBoardId(commentEntity.getBoardEntity().getId());
        commentDTO.setCreatedAt(UtilClass.dateTimeFormat(commentEntity.getCreatedAt()));
        commentDTO.setUpdatedAt(UtilClass.dateTimeFormat(commentEntity.getUpdatedAt()));
        commentDTO.setLikeCount(commentEntity.getLikeEntityList().size());
        for (LikeEntity likeEntity: likeEntityList) {
            if (likeEntity.getCommentEntity().getId().equals(commentEntity.getId())) {
                commentDTO.setIsLike(1);
            }
        }
        return commentDTO;
    }
}