package SPA.dev.Stock.enumeration;

public enum EnumVente {
    INITIATED,   // Vente initiée mais pas encore complétée.
    COMPLETED,   // Vente finalisée avec succès.
    CANCELLED,   // Vente annulée.
    PENDING,     // Vente en attente de confirmation ou d'approbation.
    REFUNDED,    // Vente remboursée.
    FAILED,      // Vente échouée (par exemple, échec de paiement).
    SHIPPED,     // Vente expédiée.
    DELIVERED    // Vente livrée au client.
}
