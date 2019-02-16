var express = require('express')
var app = express()

var queue = [];

app.get('/giff', function (req, res) {
    if(queue.length < 1){
        res.send("none");
        return;
    }

    res.send(queue.shift());
});

app.get('/send', function(req, res){
    queue.push(
        {
            username: req.query.username,
            master: req.query.master
        });
    res.send("ye");

    console.log(queue);
});


app.get('/', function(req, res){
    res.send("Yeah you can connect");
});

app.listen(8080, () => console.log(`Listening on port 8080`))
