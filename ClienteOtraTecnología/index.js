//PARA OBTENER UN CLIENTE POR ID 

var colors = require('colors/safe');
var soap = require('soap');
const http = require('http');

const hostname = '127.0.0.1';
const port = 3000;

console.log(colors.cyan('--1)---- BURCAR CLIENTE POR ID-------')); 
console.log(colors.cyan('--2)---- CREAR CLIENTE Y RETORNAR ID-------')); 


const server = http.createServer((req, res) => {

  console.clear();
  console.log(req.url);


  var opcion = req.url.substring(1, req.url.length).split('/');
  console.log(opcion);

  var url = 'http://34.125.102.171:8300/ws/cliente.wsdl';

  var args1 = {'id': '61aabe2a6bbdc20417600c66'};  
  var args2 = {
    'Cliente':{
     'tipoIdentificacion':'CED',
     'identificacion':'1727281915',
     'apellidoPaterno':'Bautista',
     'apellidoMaterno':'BRICEÑO',
     'nombre1':'Jairo',
     'nombre2':'Valentina',
     'provincia':'LOJA',
     'canton':'GONZA',
     'parroquia':'GONZA',
     'direccion':'SAN PEDRO',
     'telefono':'0958462222',
     'email':'diana@gmail.com',
     'fechaNaciemiento':'15-07-1997',
     'estadoBancaWeb':'N',
     'estado':'ACT'
    }
     
 };

  res.statusCode = 200;
  res.setHeader('Content-Type', 'text/plain');
  soap.createClient(url, function(err, client) {
    
        if (opcion[0]=='1'){

          client.getClienteById(args1, function(err, result) {
          console.clear();
          console.log(colors.cyan('------ BURCAR CLIENTE POR ID-------')); 
            console.log(result);
           // res.write('1'+JSON.stringify(result));
            res.end(''+JSON.stringify(result));
          });

        }else
          client.createdClienteReturnId(args2, function (err, result) {
            console.clear();
            console.log(colors.bgGreen('Cliente creado Correctamente'));
            console.log(result);
            console.log(colors.cyan('------ID-------'));
            console.log(result.id);
         });    

    });
 
});
 
server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});


/*
//PARA CREAR CLIENTE Y ME RETORNE EL ID
var colors = require('colors/safe');
var soap = require('soap');
const http = require('http');

const hostname = '127.0.0.1';
const port = 3000;

const server = http.createServer((req, res) => {
    
  var url = 'http://34.125.102.171:8300/ws/cliente.wsdl';
    var args = {
       'Cliente':{
        'tipoIdentificacion':'CED',
        'identificacion':'1150409397',
        'apellidoPaterno':'AREVALO',
        'apellidoMaterno':'BRICEÑO',
        'nombre1':'DIANA',
        'nombre2':'DIANA',
        'provincia':'LOJA',
        'canton':'GONZA',
        'parroquia':'GONZA',
        'direccion':'SAN PEDRO',
        'telefono':'0958462222',
        'email':'diana@gmail.com',
        'fechaNaciemiento':'15-07-1997',
        'estadoBancaWeb':'N',
        'estado':'ACT'
       }
        
    };
  
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/plain');
    soap.createClient(url, function (err, client) {
       // console.log(client.describe());

        client.createdClienteReturnId(args, function (err, result) {
            console.clear();
            console.log(colors.bgGreen('Cliente creado Correctamente'));
            console.log(result);
            console.log(colors.cyan('------ID-------'));
            console.log(result.id);

        });
    });
    //res.end('Hola Mundo\n');
});

server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});
*/

