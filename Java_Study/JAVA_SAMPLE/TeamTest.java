package JAVA_SAMPLE;

import java.util.*;

public class TeamTest {
  public static void main(String[] args) {
    TeamManagement tm = new TeamManagement();
    Scanner sc = new Scanner(System.in);
    int selector;
    do {
      System.out.println("명령어 번호를 입력하세요");
      System.out.println("1: 선수 추가");
      System.out.println("2: 선수 조회");
      System.out.println("3: 선수 포지션 변경");
      System.out.println("0: 프로그램 종료");
      selector = sc.nextInt();
      if (selector == 1) {
        System.out.println("어느 포지션 선수를 생성하시겠습니까?: ");
        String pos = sc.next();
        System.out.println("선수의 등번호를 정해주세요: ");
        int pno = sc.nextInt();
        SoccerPlayer sP = new SoccerPlayer(pos, pno);
        tm.addPlayer(sP);
        System.out.println("========추가완료========");
      } else if (selector == 2) {
        System.out.println("선수를 조회합니다.");
        System.out.print("선수 번호: ");
        int no = sc.nextInt();
        int idx = tm.findPlayer(no);
        if (idx == -1) {
          System.out.println("========================");
          System.out.println("해당하는 선수가 없습니다.");
        } else {
          System.out.println("========조회결과========");
          System.out.println("선수 번호: " + tm.sp[idx].playerNo);
          System.out.println("포지션: " + tm.sp[idx].position);
        } 
      } else if (selector == 3) {
        System.out.println("포지션을 변경합니다.");
        System.out.print("변경할 선수 번호: ");
        int no = sc.nextInt();
        System.out.print("변경할 포지션: ");
        String newpos = sc.next();
        tm.changePosition(no, newpos);
        System.out.println("========================");
      }
    }
    while (selector != 0);
  }
}
