package JAVA_SAMPLE;

interface Player {
  String national = "Korean";
  void training();
  void resting();
}

public class SoccerPlayer implements Player {
  String position;
  int playerNo;

  SoccerPlayer(String position, int playerNo) {
    this.position = position;
    this.playerNo = playerNo;
  }

  public void training() {
    System.out.println("훈련을 합니다.");
  }
  public void resting() {
    System.out.println("휴식을 취합니다.");
  }
}
