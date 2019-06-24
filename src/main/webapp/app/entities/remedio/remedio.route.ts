import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Remedio } from 'app/shared/model/remedio.model';
import { RemedioService } from './remedio.service';
import { RemedioComponent } from './remedio.component';
import { RemedioDetailComponent } from './remedio-detail.component';
import { RemedioUpdateComponent } from './remedio-update.component';
import { RemedioDeletePopupComponent } from './remedio-delete-dialog.component';
import { IRemedio } from 'app/shared/model/remedio.model';

@Injectable({ providedIn: 'root' })
export class RemedioResolve implements Resolve<IRemedio> {
  constructor(private service: RemedioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IRemedio> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Remedio>) => response.ok),
        map((remedio: HttpResponse<Remedio>) => remedio.body)
      );
    }
    return of(new Remedio());
  }
}

export const remedioRoute: Routes = [
  {
    path: '',
    component: RemedioComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Remedios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: RemedioDetailComponent,
    resolve: {
      remedio: RemedioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Remedios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: RemedioUpdateComponent,
    resolve: {
      remedio: RemedioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Remedios'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: RemedioUpdateComponent,
    resolve: {
      remedio: RemedioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Remedios'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const remedioPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: RemedioDeletePopupComponent,
    resolve: {
      remedio: RemedioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Remedios'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
