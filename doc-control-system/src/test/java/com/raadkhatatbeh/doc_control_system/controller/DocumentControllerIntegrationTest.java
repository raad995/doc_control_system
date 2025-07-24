package com.raadkhatatbeh.doc_control_system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raadkhatatbeh.doc_control_system.controller.path.ApiPaths;
import com.raadkhatatbeh.doc_control_system.dto.BatchAccessCheckRequest;
import com.raadkhatatbeh.doc_control_system.model.DocumentModel;
import com.raadkhatatbeh.doc_control_system.model.DocumentPermissionModel;
import com.raadkhatatbeh.doc_control_system.model.FileType;
import com.raadkhatatbeh.doc_control_system.model.PermissionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * DocumentControllerIntegrationTest.
 *
 * @author Raad khatatbeh
 * @since 23/7/2025
 */
@SpringBootTest
@AutoConfigureMockMvc
class DocumentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public final String path = ApiPaths.Document.DOCUMENTS;
    public final String ADMIN = "admin";
    public final String REQUEST_HEADER = "X-User";

    @Test
    void testCreateDocument() throws Exception {

        DocumentModel model = new DocumentModel();
        model.setName("Sample Document");
        model.setContent("This is the content of the document.");
        model.setFileType(FileType.CSV);
        model.setCreatedBy("admin");

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(model))
                        .header(REQUEST_HEADER, ADMIN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sample Document"));
    }

    @Test
    void testGetDocuments() throws Exception {

        mockMvc.perform(get(path)
                        .header(REQUEST_HEADER, ADMIN))
                .andExpect(status().isOk());
    }

    @Test
    void testGetDocuments_NotAdmin() throws Exception {

        mockMvc.perform(get(path)
                        .header(REQUEST_HEADER, "user2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sample Document"));
    }

    @Test
    void testGetDocumentById() throws Exception {


        mockMvc.perform(get(path + "/90000")
                        .header(REQUEST_HEADER, "user2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sample Document"));
    }

    @Test
    void testDeleteDocument() throws Exception {

        mockMvc.perform(delete(path + ApiPaths.Document.GET_DOCUMENT, 20000L)
                        .header("X-User", ADMIN))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGrantPermission() throws Exception {

        DocumentPermissionModel model = new DocumentPermissionModel();
        model.setUsername("user2");
        model.setPermission(PermissionType.READ);

        mockMvc.perform(post(path + ApiPaths.Document.GRANT_PERMISSION, 90000)
                        .header(REQUEST_HEADER, ADMIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(model)))
                .andExpect(status().isOk());
    }

    @Test
    void testBatchAccessCheck() throws Exception {

        BatchAccessCheckRequest request = new BatchAccessCheckRequest();
        request.setDocumentIds(List.of(90000L, 2L));
        request.setPermission(PermissionType.READ);

        mockMvc.perform(post(path + ApiPaths.Document.ACCESS_CHECK)
                        .header(REQUEST_HEADER, "user2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
