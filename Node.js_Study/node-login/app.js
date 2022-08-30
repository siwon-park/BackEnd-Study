const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const home = require("./routes/home/index");

// 앱 세팅
app.set('views', './views');
app.set('view engine', 'ejs');

app.use(express.static(`${__dirname}/public`)) // 정적 파일 위치 등록
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true})); // url을 통해 전달되는 데이터에 한글, 공백과 같은 문자가 포함될 경우 제대로 인식되지 않는 것을 해결
app.use("/", home); // use => 미들웨어를 등록해주는 메서드

module.exports = app;
