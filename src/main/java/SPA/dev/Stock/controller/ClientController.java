package SPA.dev.Stock.controller;

import SPA.dev.Stock.dto.ClientDto;
import SPA.dev.Stock.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/client")
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/list")
    public ResponseEntity<List<ClientDto>> getClients(){
        return ResponseEntity.ok(clientService.getAll());
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable Long id){
        return ResponseEntity.ok(clientService.getClient(id));
    }

    @PostMapping("/add")
    public ResponseEntity<ClientDto> addClient(@Valid @RequestBody ClientDto clientDto){

        return ResponseEntity.ok(clientService.addClient(clientDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ClientDto> removeClient(@PathVariable Long id){
        return ResponseEntity.ok(clientService.removeClient(id));
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDto clientDto){
        return ResponseEntity.ok(clientService.updateClient(id,clientDto));
    }



}