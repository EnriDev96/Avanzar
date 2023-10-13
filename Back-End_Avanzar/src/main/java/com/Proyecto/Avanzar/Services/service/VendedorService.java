package com.Proyecto.Avanzar.Services.service;

import com.Proyecto.Avanzar.Models.Vendedor;

import java.util.List;

public interface VendedorService extends GenericService<Vendedor, Long>{

    Vendedor getVendedoresByUsuarioId(Long userId);
}
