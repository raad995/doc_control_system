package com.raadkhatatbeh.doc_control_system.modelMapper;

import com.raadkhatatbeh.doc_control_system.model.DocumentPermissionModel;
import com.raadkhatatbeh.doc_control_system.repo.entity.DocumentPermission;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * DocumentPermission Model Mapper.
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
public class DocumentPermissionModelMapper {

    /**
     * Converts a {@link DocumentPermissionModel} to a {@link DocumentPermission} entity.
     *
     * @param model the model to convert
     * @return the converted entity, or {@code null} if the model is {@code null}
     */
    public static DocumentPermission toEntity(final DocumentPermissionModel model) {

        if (model == null) {
            return null;
        }

        final DocumentPermission entity = new DocumentPermission();
        entity.setId(model.getId());
        entity.setUsername(model.getUsername());
        entity.setPermissionType(model.getPermission());
        entity.setCreatedAt(model.getCreatedAt());

        return entity;
    }

    /**
     * Converts a {@link DocumentPermission} entity to a {@link DocumentPermissionModel}.
     *
     * @param entity the entity to convert
     * @return the converted model, or {@code null} if the entity is {@code null}
     */
    public static DocumentPermissionModel toModel(final DocumentPermission entity) {

        if (entity == null) {
            return null;
        }

        final DocumentPermissionModel model = new DocumentPermissionModel();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setPermission(entity.getPermissionType());
        model.setCreatedAt(entity.getCreatedAt());

        return model;
    }

    /**
     * Converts a list of {@link DocumentPermissionModel}s to a list of {@link DocumentPermission} entities.
     *
     * @param models the list of models to convert
     * @return a list of converted entities, or an empty list if input is {@code null}
     */
    public static List<DocumentPermission> toEntity(final List<DocumentPermissionModel> models) {

        return Optional.ofNullable(models)
                .orElse(List.of())
                .stream()
                .map(DocumentPermissionModelMapper::toEntity)
                .collect(Collectors.toList());
    }

    /**
     * Converts a list of {@link DocumentPermission} entities to a list of {@link DocumentPermissionModel}s.
     *
     * @param entities the list of entities to convert
     * @return a list of converted models, or an empty list if input is {@code null}
     */
    public static List<DocumentPermissionModel> toModel(final List<DocumentPermission> entities) {

        return Optional.ofNullable(entities)
                .orElse(List.of())
                .stream()
                .map(DocumentPermissionModelMapper::toModel)
                .collect(Collectors.toList());
    }
}