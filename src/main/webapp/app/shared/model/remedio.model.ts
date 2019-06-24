import { IDoenca } from 'app/shared/model/doenca.model';
import { IEstoque } from 'app/shared/model/estoque.model';
import { IReserva } from 'app/shared/model/reserva.model';

export interface IRemedio {
  id?: number;
  nome?: string;
  laboratorio?: string;
  doencas?: IDoenca[];
  estoque?: IEstoque;
  reserva?: IReserva;
}

export class Remedio implements IRemedio {
  constructor(
    public id?: number,
    public nome?: string,
    public laboratorio?: string,
    public doencas?: IDoenca[],
    public estoque?: IEstoque,
    public reserva?: IReserva
  ) {}
}
