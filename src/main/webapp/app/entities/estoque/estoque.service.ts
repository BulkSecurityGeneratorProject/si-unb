import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEstoque } from 'app/shared/model/estoque.model';

type EntityResponseType = HttpResponse<IEstoque>;
type EntityArrayResponseType = HttpResponse<IEstoque[]>;

@Injectable({ providedIn: 'root' })
export class EstoqueService {
  public resourceUrl = SERVER_API_URL + 'api/estoques';

  constructor(protected http: HttpClient) {}

  create(estoque: IEstoque): Observable<EntityResponseType> {
    return this.http.post<IEstoque>(this.resourceUrl, estoque, { observe: 'response' });
  }

  update(estoque: IEstoque): Observable<EntityResponseType> {
    return this.http.put<IEstoque>(this.resourceUrl, estoque, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstoque>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstoque[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
