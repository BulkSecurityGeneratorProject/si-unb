import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRemedio } from 'app/shared/model/remedio.model';

type EntityResponseType = HttpResponse<IRemedio>;
type EntityArrayResponseType = HttpResponse<IRemedio[]>;

@Injectable({ providedIn: 'root' })
export class RemedioService {
  public resourceUrl = SERVER_API_URL + 'api/remedios';

  constructor(protected http: HttpClient) {}

  create(remedio: IRemedio): Observable<EntityResponseType> {
    return this.http.post<IRemedio>(this.resourceUrl, remedio, { observe: 'response' });
  }

  update(remedio: IRemedio): Observable<EntityResponseType> {
    return this.http.put<IRemedio>(this.resourceUrl, remedio, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRemedio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRemedio[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
