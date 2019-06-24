import { IEstoque } from 'app/shared/model/estoque.model';
import { IReserva } from 'app/shared/model/reserva.model';

export interface IFarmacia {
  id?: number;
  nome?: string;
  cidade?: string;
  endereco?: string;
  longitude?: number;
  latitude?: number;
  estoque?: IEstoque;
  reserva?: IReserva;
}

export class Farmacia implements IFarmacia {
  constructor(
    public id?: number,
    public nome?: string,
    public cidade?: string,
    public endereco?: string,
    public longitude?: number,
    public latitude?: number,
    public estoque?: IEstoque,
    public reserva?: IReserva
  ) {}
}
