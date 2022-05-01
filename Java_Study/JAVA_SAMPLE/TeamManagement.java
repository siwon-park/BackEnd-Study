package JAVA_SAMPLE;

public class TeamManagement {
  SoccerPlayer[] sp = new SoccerPlayer[11];
  int size = 0;
  
  int findPlayer(int num) {
    int no = -1;
    for (int i = 0; i < size; i++) {
      if (sp[i].playerNo == num) {
        no = i;
        break;
      }
    }
    return no;
  }

  void addPlayer(SoccerPlayer p) {
    sp[size] = p;
    System.out.println(sp[size].playerNo + "번 " + sp[size].position + "가 추가되었습니다");
    size += 1;
  }

  SoccerPlayer getPlayer(int num) {
    int idx = findPlayer(num);
    if (idx == -1){
      return null;
    }
    return sp[idx];
  }

  void changePosition(int no, String pos) {
    int idx = findPlayer(no);
    System.out.println("========================");
    if (idx == -1) {
      System.out.println("존재하지 않는 선수입니다. 포지션을 변경할 수 없습니다.");
    } else {
      System.out.println("포지션이 "+ sp[idx].position + "에서 " + pos +"으로 변경되었습니다.");
      sp[idx].position = pos;
    }
  }
}
