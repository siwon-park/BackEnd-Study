package JAVA_0407.dao;
import java.util.*;

import JAVA_0407.exception.VideoNotFoundException;
import JAVA_0407.model.Video;

public interface IVideoDao {
  // 비디오 목록을 보여주기 위해 모든 비디오 정보를 가져오는 기능
  List<Video> selectVideo();
  // 
  Video selectVideoByNo(int no) throws VideoNotFoundException;
}
