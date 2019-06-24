import { Moment } from 'moment';
import { IRemedio } from 'app/shared/model/remedio.model';
import { IFarmacia } from 'app/shared/model/farmacia.model';

export interface IReserva {
  id?: number;
  cpfPaciente?: number;
  nomePaciente?: string;
  quantidade?: number;
  horaReserva?: Moment;
  remedios?: IRemedio[];
  farmacias?: IFarmacia[];
}

export class Reserva implements IReserva {
  constructor(
    public id?: number,
    public cpfPaciente?: number,
    public nomePaciente?: string,
    public quantidade?: number,
    public horaReserva?: Moment,
    public remedios?: IRemedio[],
    public farmacias?: IFarmacia[]
  ) {}
}
