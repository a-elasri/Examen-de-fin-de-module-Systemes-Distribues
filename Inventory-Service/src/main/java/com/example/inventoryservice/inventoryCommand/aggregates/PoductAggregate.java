package com.example.inventoryservice.inventoryCommand.aggregates;

import com.example.commonapi.commands.CreateProductCommand;
import com.example.commonapi.commands.UpdateProductCommand;
import com.example.commonapi.enums.ProductEtat;
import com.example.commonapi.events.ProductCreatedEvent;
import com.example.commonapi.events.ProductUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
public class PoductAggregate {
    @AggregateIdentifier
    private String id;
    private String nom ;
    private double prix ;
    private  int qteStock  ;
    private ProductEtat etat ;

    public PoductAggregate() {
    }

    @CommandHandler
    public PoductAggregate(CreateProductCommand command) {
        AggregateLifecycle.apply(
                new ProductCreatedEvent(
                        command.getId(),
                        command.getNom(),
                        command.getPrix(),
                        command.getQte(),
                        command.getEtat()
                        )
        );
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.id = event.getId();
        this.nom = event.getNom();
        this.prix = event.getPrix();
        this.etat = event.getEtat();
        this.qteStock = event.getQte();

    }

    @CommandHandler
    public void handle(UpdateProductCommand command) {
        AggregateLifecycle.apply(
                new ProductUpdatedEvent(
                        command.getId(),
                        command.getNom(),
                        command.getPrix(),
                        command.getQte(),
                        command.getEtat()
                )
        );
    }

    @EventSourcingHandler
    public void on(ProductUpdatedEvent event) {
        this.id = event.getId();
        this.nom = event.getNom();
        this.prix = event.getPrix();
        this.qteStock = event.getQte();
        this.etat = event.getEtat();
    }

}
