package JAVA_0409;

public class InventoryManagement {
  Inventory[] inventory = new Inventory[200];
  int size = 0;
  
  int findInventory(String name) {
    int idx = -1;
    for (int i = 0; i < size; i++) {
      if (name.equals(inventory[i].name)) {
        idx = i;
        break;
      }
    }
    return idx;
  }

  void addInventory(Inventory inv) {
    inventory[size] = inv;
    size += 1;
  }

  Inventory getInventory(String name) {
    int idx = findInventory(name);
    if (idx == -1) {
      return null;
    } else {
      return inventory[idx];
    }
  }

  void changeInventory(String name, int quantity) {
    int idx = findInventory(name);
    System.out.println("========================");
    if (idx == -1) {
      System.out.println("해당 제품이 없습니다. 재고를 변경할 수 없습니다.");
    } else {
      inventory[idx].quantity = quantity;
      System.out.println("재고가 변경되었습니다.");
    }
  }

}
