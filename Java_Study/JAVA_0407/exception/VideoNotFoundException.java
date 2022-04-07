package JAVA_0407.exception;

public class VideoNotFoundException extends Exception {
  public VideoNotFoundException(int no) {
    super(no + " 영상을 찾을 수 없습니다. ");
  }
  
}
