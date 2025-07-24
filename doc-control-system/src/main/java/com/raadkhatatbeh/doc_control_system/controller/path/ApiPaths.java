package com.raadkhatatbeh.doc_control_system.controller.path;

/**
 * Paths class containing endpoint constants for the Document API.
 *
 * @author Raad khatatbeh
 * @since 22/7/2025
 */
public class ApiPaths {

    /**
     * Document Document
     */
    public static class Document {

        /**
         * Base URI path for document-related endpoints.
         */
        public static final String DOCUMENTS = "/documents";
        public static final String GET_DOCUMENT = "/{documentId}";
        public static final String GRANT_PERMISSION = GET_DOCUMENT + "/grant";
        public static final String ACCESS_CHECK = "/access-check";

    }
}
