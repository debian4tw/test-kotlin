import express from 'express';
//import exphbs from 'express-handlebars'
const app: express.Application = express();
const port: any = process.env.PORT || 3000;
import * as path from 'path';

app.use(express.static(__dirname + '/../src/views/'));

app.get('/',  (req, res) => {
  res.sendFile(path.join(__dirname+'/../src/views/home.html'));
});

app.listen(3000, function () {
console.log('App is listening on port 3000!');
});



