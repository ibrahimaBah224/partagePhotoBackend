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
@RequestMapping("/api")
public class ClientController {
    private final ClientService clientService;

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDto>> getClients(){
        return ResponseEntity.ok(clientService.getAll());
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientDto> getClient(@PathVariable Long id){
        return ResponseEntity.ok(clientService.getClient(id));
    }

    @PostMapping("/clients")
    public ResponseEntity<ClientDto> addClient(@Valid @RequestBody ClientDto clientDto){
        ClientDto client = clientService.addClient(clientDto);
        return ResponseEntity.created(URI.create("/clients/"+client.getId())).body(client);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<ClientDto> removeClient(@PathVariable Long id){
        return ResponseEntity.ok(clientService.removeClient(id));
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable Long id, @Valid @RequestBody ClientDto clientDto){
        return ResponseEntity.ok(clientService.updateClient(id,clientDto));
    }

}