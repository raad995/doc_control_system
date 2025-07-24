package com.raadkhatatbeh.doc_control_system.repo;

import com.raadkhatatbeh.doc_control_system.model.PermissionType;
import com.raadkhatatbeh.doc_control_system.repo.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Document Repository.
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {

    /**
     * Retrieves all documents accessible by a specific user
     *
     * @param username       the username of the user requesting access
     * @param permissionType the required permission type READ
     * @return a list of matching {@link Document} entities
     */
    @Query("SELECT d FROM Document d JOIN d.accessibleUsers dp " +
            "WHERE dp.username = :username AND dp.permissionType = :permissionType")
    List<Document> findByUsernameAndPermission(@Param("username") String username,
                                               @Param("permissionType") PermissionType permissionType);

    /**
     * Retrieves a list of documents that have IDs matching the provided list.
     *
     * @param ids the list of document IDs to retrieve
     * @return a list of Document entities with matching IDs
     */
    @Query("SELECT d FROM Document d WHERE d.id IN :ids")
    List<Document> findByIds(@Param("ids") List<Long> ids);
}
