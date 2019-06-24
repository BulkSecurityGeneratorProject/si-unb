import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDoenca } from 'app/shared/model/doenca.model';

type EntityResponseType = HttpResponse<IDoenca>;
type EntityArrayResponseType = HttpResponse<IDoenca[]>;

@Injectable({ providedIn: 'root' })
export class DoencaService {
  public resourceUrl = SERVER_API_URL + 'api/doencas';

  constructor(protected http: HttpClient) {}

  create(doenca: IDoenca): Observable<EntityResponseType> {
    return this.http.post<IDoenca>(this.resourceUrl, doenca, { observe: 'response' });
  }

  update(doenca: IDoenca): Observable<EntityResponseType> {
    return this.http.put<IDoenca>(this.resourceUrl, doenca, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDoenca>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDoenca[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
