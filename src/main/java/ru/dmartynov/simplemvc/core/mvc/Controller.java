package ru.dmartynov.simplemvc.core.mvc;

import com.google.inject.Inject;
import spark.Request;
import spark.Response;

/**
 * Created by Дмитрий on 29.04.2014.
 */
public abstract class Controller {
    @Inject
    protected Request request;
    @Inject
    protected Response response;
}
