package JAVA_0407.model;

public class Video {
  private int no;
  private String title;
  private String part;
  private String url;

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPart() {
    return part;
  }

  public void setPart(String part) {
    this.part = part;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return "Video [no=" + no + ", title =" + title + ", part =" + part + ", url =" + url +"]";
  }
}
