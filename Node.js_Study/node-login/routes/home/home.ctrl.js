// const UserStroage = require("../../models/UserStorage");
const User = require("../../models/Users");

const output = {
  home: (req, res) => {
    res.render("home/index");
  },
  login: (req, res) => {
    res.render("home/login");
  }
};

const process = {
  login: (req, res) => {
    const user = new User(req.body);
    const response = user.login();
    console.log(response);
    return res.json(response);

    // const id = req.body.id;
    // const pw = req.body.pw;

    // // 유저의 id, pw 정보를 갖고옴
    // const users = UserStroage.getUsers("id", "pw");

    // if (users.id.includes(id)) {
    //   const idx = users.id.indexOf(id);
    //   if (users.pw[idx] === pw) {
    //     return res.json({
    //       success: true,
    //     });
    //   }
    // }

    // return res.json({
    //   success: false,
    //   msg: "로그인에 실패했습니다",
    // });
  },
};

module.exports = {
  output,
  process,
};
