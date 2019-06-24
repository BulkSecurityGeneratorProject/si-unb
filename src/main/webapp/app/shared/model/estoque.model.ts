import { IFarmacia } from 'app/shared/model/farmacia.model';
import { IRemedio } from 'app/shared/model/remedio.model';

export interface IEstoque {
  id?: number;
  quantidade?: number;
  farmacias?: IFarmacia[];
  remedios?: IRemedio[];
}

export class Estoque implements IEstoque {
  constructor(public id?: number, public quantidade?: number, public farmacias?: IFarmacia[], public remedios?: IRemedio[]) {}
}
