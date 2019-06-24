import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFarmacia } from 'app/shared/model/farmacia.model';

type EntityResponseType = HttpResponse<IFarmacia>;
type EntityArrayResponseType = HttpResponse<IFarmacia[]>;

@Injectable({ providedIn: 'root' })
export class FarmaciaService {
  public resourceUrl = SERVER_API_URL + 'api/farmacias';

  constructor(protected http: HttpClient) {}

  create(farmacia: IFarmacia): Observable<EntityResponseType> {
    return this.http.post<IFarmacia>(this.resourceUrl, farmacia, { observe: 'response' });
  }

  update(farmacia: IFarmacia): Observable<EntityResponseType> {
    return this.http.put<IFarmacia>(this.resourceUrl, farmacia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFarmacia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFarmacia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
