const id = document.querySelector('#id');
const pw = document.querySelector('#password');
const loginBtn = document.querySelector("#button");

loginBtn.addEventListener("click", login);

function login() {
  const req = {
    id: id.value,
    pw: pw.value,
  }
  
  fetch("/login", {
    method: "post",
    headers: {
      "Content-Type": "application/json", // json 타입으로 받겠다고 명시
    },
    body: JSON.stringify(req)
  })
  .then((res) => res.json()) // res.json의 반환값은 Promise 객체임(아래의 res는 Promise 객체인 res를 말함)
  // .then(console.log(res))
  .then((res) => {
    if (res.success) {
      location.href = "/";
    } else {
      alert(res.msg);
    }
  })
  .catch((err) => {
    // console.error(new Error("로그인 중 에러 발생"));
    console.error("로그인 중 에러 발생");
  })
}