package JAVA_0409;

import java.util.*;

public class InventoryTest {

  // static Inventory[] inventory = new Inventory[200];
  // static int size = 0;
  // // 제품명을 입력 받아 제품의 인덱스(제품 번호를 반환받는 함수)
  // static int getInventory(String name) {
  //   int idx = -1;
  //   for (int i = 0; i < size; i++) {
  //     if (name.equals(inventory[i].name)) {
  //       idx = i;
  //       break;
  //     }
  //   }
  //   return idx;
  // }
  // // 제품의 재고를 변경하는 함수
  // static void changeInventory(String name, int quantity) {
  //   int idx = getInventory(name);
  //   System.out.println("========================");
  //   if (idx == -1) {
  //     System.out.println("해당 제품이 없습니다. 재고를 변경할 수 없습니다.");
  //   } else {
  //     inventory[idx].quantity = quantity;
  //     System.out.println("재고가 변경되었습니다.");
  //   }
  // }

  public static void main(String[] args) {
    InventoryManagement im = new InventoryManagement();
    Scanner sc = new Scanner(System.in);
    int selector;
    do {
      System.out.println("명령어 번호를 입력하세요");
      System.out.println("1: 제품/재고 추가");
      System.out.println("2: 제품/재고 조회");
      System.out.println("3: 제품/재고 변경");
      System.out.println("0: 프로그램 종료");
      selector = sc.nextInt();
      if (selector == 1) {
        System.out.println("제품/재고를 추가합니다.");
        System.out.print("제품: ");
        String name = sc.next();
        System.out.print("재고: ");
        int quantity = sc.nextInt();
        Inventory inv = new Inventory();
        inv.name = name;
        inv.quantity = quantity;
        im.addInventory(inv);
        System.out.println("========추가완료========");
      } else if (selector == 2) {
        System.out.println("제품의 재고를 조회합니다.");
        System.out.print("제품: ");
        String name = sc.next();
        int idx = im.findInventory(name);
        if (idx == -1) {
          System.out.println("========================");
          System.out.println("해당하는 제품이 없습니다.");
        } else {
          System.out.println("========조회결과========");
          System.out.println("제품 번호: " + idx);
          System.out.println("제품 명: " + im.inventory[idx].name);
          System.out.println("재고: " + im.inventory[idx].quantity);
        }
        System.out.println("========================");
      } else if (selector == 3) {
        System.out.println("재고를 변경합니다.");
        System.out.print("제품: ");
        String name = sc.next();
        System.out.print("변경할 재고량: ");
        int quantity = sc.nextInt();
        im.changeInventory(name, quantity);
        System.out.println("========================");
      }
    } while (selector != 0);
  }
}