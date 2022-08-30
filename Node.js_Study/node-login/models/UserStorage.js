class UserStroage {
  // 정적 변수화 UserStorage.users로 접근 가능
  // #는 private 변수로 바꿈
  static #users = {
    id: ["zow777", "siwonpark"],
    pw: ["asd123123", "1234"]
  }

  static getUsers(...params) {
    const users = this.#users;
    // 필요한 데이터만 받기위해 reduce를 사용함
    const newUsers = params.reduce((newUsers, param) => {
      if (users.hasOwnProperty(param)) {
        newUsers[param] = users[param];
      }
      return newUsers;
    }, {})

    return newUsers
    // return this.#users;
  }

  static getUserInfo(id) {
    const users = this.#users;
    const idx = users.id.indexOf(id);
    const usersKeys = Object.keys(users); // [id, pw, ..]와 같이 키 값만 배열로 뽑아옴
    const userInfo = usersKeys.reduce((newUser, info) => {
      newUser[info] = users[info][idx];
      return newUser;
    }, {});

    return userInfo;
  }
}

module.exports = UserStroage;