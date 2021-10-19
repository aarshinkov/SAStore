package com.sastore.web.utils;

import com.sastore.web.entities.UserEntity;
import java.util.Comparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class LoggedUserComparator implements Comparator<Object> {

  private final Logger log = LoggerFactory.getLogger(getClass());

  public static final int LOGGED_ON = 1;
  public static final int USERNAME = 2;

  private int sortBy;

  public LoggedUserComparator(int sortBy) {
    this.sortBy = sortBy;
  }

  public LoggedUserComparator() {
    this.sortBy = 1;
  }

  @Override
  public int compare(Object o1, Object o2) {
    UserEntity user1 = (UserEntity) o1;
    UserEntity user2 = (UserEntity) o2;

//    switch (sortBy) {
//      case 1:
//        return user1.getLoggedOn().compareTo(user2.getLoggedOn()) * -1;
//      case 2:
//        return user1.getUsername().compareTo(user2.getUsername());
//    }
    return 0;
  }
}
