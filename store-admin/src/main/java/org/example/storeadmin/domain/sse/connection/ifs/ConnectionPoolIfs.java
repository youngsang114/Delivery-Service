package org.example.storeadmin.domain.sse.connection.ifs;

import org.example.storeadmin.domain.sse.connection.model.UserSseConnection;

public interface ConnectionPoolIfs<T,R> {

    void addSession(T key,R session);
    R getSession(String uniqueKey);

    void onCompletionCallback(R session);
}
