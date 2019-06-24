import { IRemedio } from 'app/shared/model/remedio.model';

export interface IDoenca {
  id?: number;
  nome?: string;
  cid?: string;
  estoques?: IRemedio[];
}

export class Doenca implements IDoenca {
  constructor(public id?: number, public nome?: string, public cid?: string, public estoques?: IRemedio[]) {}
}
