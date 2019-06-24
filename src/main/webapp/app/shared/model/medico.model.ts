export interface IMedico {
  id?: number;
  nome?: string;
  usuario?: string;
  email?: string;
  matricula?: string;
  senha?: string;
}

export class Medico implements IMedico {
  constructor(
    public id?: number,
    public nome?: string,
    public usuario?: string,
    public email?: string,
    public matricula?: string,
    public senha?: string
  ) {}
}
