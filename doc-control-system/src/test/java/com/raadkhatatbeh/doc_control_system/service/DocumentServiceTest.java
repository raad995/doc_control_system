package com.raadkhatatbeh.doc_control_system.service;

import com.raadkhatatbeh.doc_control_system.exception.AccessDeniedException;
import com.raadkhatatbeh.doc_control_system.exception.ResourceNotFoundException;
import com.raadkhatatbeh.doc_control_system.model.DocumentModel;
import com.raadkhatatbeh.doc_control_system.model.DocumentPermissionModel;
import com.raadkhatatbeh.doc_control_system.model.PermissionType;
import com.raadkhatatbeh.doc_control_system.repo.DocumentRepo;
import com.raadkhatatbeh.doc_control_system.repo.entity.Document;
import com.raadkhatatbeh.doc_control_system.repo.entity.DocumentPermission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * DocumentServiceTest.
 *
 * @author Raad khatatbeh
 * @since 23/7/2025
 */
public class DocumentServiceTest {

    @Mock
    private DocumentRepo documentRepo;

    @InjectMocks
    private DocumentService documentService;

    private final String ADMIN = "admin";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDocument_adminValidDocument_success() {
        DocumentModel model = new DocumentModel();
        model.setName("Valid Document");
        model.setContent("Some valid content");

        when(documentRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);

        DocumentModel saved = documentService.createDocument(model, ADMIN);

        assertNotNull(saved);
        assertEquals("Valid Document", saved.getName());
    }

    @Test
    void testCreateDocument_nonAdmin_throwsAccessDenied() {
        DocumentModel model = new DocumentModel();
        model.setName("Doc");
        model.setContent("Content");

        assertThrows(AccessDeniedException.class, () ->
                documentService.createDocument(model, "user1"));
    }

    @Test
    void testGetDocuments_asAdmin_returnsAll() {
        List<Document> mockDocs = List.of(new Document(), new Document());
        when(documentRepo.findAll()).thenReturn(mockDocs);

        List<DocumentModel> result = documentService.getDocuments(ADMIN);
        assertEquals(2, result.size());
    }

    @Test
    void testGetDocuments_asUser_returnsAccessible() {
        when(documentRepo.findByUsernameAndPermission("user1", PermissionType.READ))
                .thenReturn(List.of(new Document()));

        List<DocumentModel> result = documentService.getDocuments("user1");
        assertEquals(1, result.size());
    }

    @Test
    void testGetDocument_asAdmin_success() {
        Document doc = new Document();
        doc.setId(1L);

        when(documentRepo.findById(1L)).thenReturn(Optional.of(doc));

        DocumentModel result = documentService.getDocument(1L, ADMIN);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetDocument_notFound_throwsResourceFoundException() {
        when(documentRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () ->
                documentService.getDocument(1L, ADMIN));
    }

    @Test
    void testDeleteDocument_withPermission_success() {
        Document doc = new Document();
        doc.setId(1L);

        DocumentPermission permission = new DocumentPermission();
        permission.setUsername("user1");
        permission.setPermissionType(PermissionType.DELETE);

        doc.getAccessibleUsers().add(permission);

        when(documentRepo.findById(1L)).thenReturn(Optional.of(doc));

        documentService.deleteDocument(1L, "user1");

        verify(documentRepo, times(1)).delete(doc);
    }

    @Test
    void testGrantPermission_admin_grantsSuccessfully() {
        DocumentPermissionModel permissionModel = new DocumentPermissionModel();
        permissionModel.setUsername("user1");
        permissionModel.setPermission(PermissionType.READ);

        Document doc = new Document();
        doc.setAccessibleUsers(new ArrayList<>());

        when(documentRepo.findById(1L)).thenReturn(Optional.of(doc));

        documentService.grantPermission(1L, ADMIN, permissionModel);
        verify(documentRepo, times(1)).save(doc);
        assertEquals(1, doc.getAccessibleUsers().size());
    }

    @Test
    void testBatchAccessCheck_admin_returnsAll() {
        List<Long> ids = List.of(1L, 2L, 3L);
        List<Long> result = documentService.batchAccessCheck(ADMIN, PermissionType.READ, ids);
        assertEquals(ids, result);
    }

    @Test
    void testBatchAccessCheck_user_returnsFiltered() {
        Document d1 = new Document();
        d1.setId(1L);
        Document d3 = new Document();
        d3.setId(3L);

        when(documentRepo.findByUsernameAndPermission("user1", PermissionType.READ))
                .thenReturn(List.of(d1, d3));

        List<Long> result = documentService.batchAccessCheck("user1", PermissionType.READ, List.of(1L, 2L, 3L));
        assertEquals(List.of(1L, 3L), result);
    }
}