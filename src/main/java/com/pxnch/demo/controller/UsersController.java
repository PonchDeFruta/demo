package com.pxnch.demo.controller;

import com.pxnch.demo.models.Saldo;
import com.pxnch.demo.models.User;
import com.pxnch.demo.response.ResponseMessage;
import com.pxnch.demo.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private Saldo saldo;
    @GetMapping("/obtenerUsers")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<List<User>>(userService.getUsers(), HttpStatus.OK);
    }
    @PostMapping("/crearUser")
    public ResponseEntity<User> crearUsuario(@RequestBody User user) {
        return new ResponseEntity<User>(userService.createUser(user), HttpStatus.CREATED);
    }
    @PutMapping(value="/updateUser/{nombreUsuario}")
    public ResponseEntity<User> updateUser(@PathVariable("nombreUsuario") String username, @RequestBody User user){
        return new ResponseEntity<User>(userService.updateUser(user, username),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{nombreUsuario}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable("nombreUsuario")String Username){
        userService.deleteUser(Username);
        return new ResponseEntity<ResponseMessage>(new ResponseMessage("Usuario Borrado "),HttpStatus.NO_CONTENT);
    }
    @PutMapping(value="/abonarSaldo/{nombreUsuario}")
    public ResponseEntity<User> agregarSaldoUser(@PathVariable("nombreUsuario") String username, @RequestBody Saldo saldo){
        return new ResponseEntity<User>(userService.agregarSaldoUser(saldo, username),HttpStatus.OK);
    }
    @PutMapping(value="/cargarSaldo/{nombreUsuario}")
    public ResponseEntity<User> restarSaldoUser(@PathVariable("nombreUsuario") String username, @RequestBody Saldo saldo){
        return new ResponseEntity<User>(userService.restarSaldoUser(saldo, username),HttpStatus.OK);
    }

}
