package com.batch.service;

import java.io.IOException;

/**
 * Interface SecurityService
 */
public interface SecurityService
{
    /**
     * Authentifie le batch à l'api
     * @return jwt
     * @throws IOException
     * @throws InterruptedException
     */
    String authticate() throws IOException, InterruptedException;

}
