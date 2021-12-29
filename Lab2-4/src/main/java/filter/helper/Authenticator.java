package filter.helper;

import entity.User;

public interface Authenticator {

    boolean hasAuthority(User user, String commandName);

}
