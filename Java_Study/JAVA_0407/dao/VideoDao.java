package JAVA_0407.dao;
import JAVA_0407.exception.VideoNotFoundException;
import JAVA_0407.model.*;
import java.util.*;

public class VideoDao implements IVideoDao {

  private static VideoDao instance = new VideoDao();
  public static VideoDao getInstance() {
    return instance;
  }

  private VideoDao() {
    
  }

  @Override
  public List<Video> selectVideo() {
    return null;
  }

  @Override
  public Video selectVideoByNo(int no) throws VideoNotFoundException {
    return null;
  }
}
