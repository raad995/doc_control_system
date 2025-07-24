package com.raadkhatatbeh.doc_control_system.modelMapper;

import com.raadkhatatbeh.doc_control_system.model.DocumentModel;
import com.raadkhatatbeh.doc_control_system.repo.entity.Document;

/**
 * Document Model Mapper.
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
public class DocumentModelMapper {

    /**
     * Converts a {@link DocumentModel} to a {@link Document} entity.
     *
     * @param model the {@link DocumentModel} to convert
     * @return the converted {@link Document} entity, or {@code null} if the model is {@code null}
     */
    public static Document toEntity(final DocumentModel model) {

        if (model == null) {
            return null;
        }

        final Document entity = new Document();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setFileType(model.getFileType());
        entity.setContent(model.getContent());
        entity.setAccessibleUsers(DocumentPermissionModelMapper.toEntity(model.getAccessibleUsers()));
        entity.setCreatedBy(model.getCreatedBy());
        entity.setCreatedAt(model.getCreatedAt());
        entity.setUpdatedAt(model.getUpdatedAt());

        return entity;
    }

    /**
     * Converts a {@link Document} entity to a {@link DocumentModel}.
     *
     * @param entity the {@link Document} entity to convert
     * @return the converted {@link DocumentModel}, or {@code null} if the entity is {@code null}
     */
    public static DocumentModel toModel(final Document entity) {

        if (entity == null) {
            return null;
        }

        final DocumentModel model = new DocumentModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setFileType(entity.getFileType());
        model.setAccessibleUsers(DocumentPermissionModelMapper.toModel(entity.getAccessibleUsers()));
        model.setCreatedBy(entity.getCreatedBy());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());

        return model;
    }
}
