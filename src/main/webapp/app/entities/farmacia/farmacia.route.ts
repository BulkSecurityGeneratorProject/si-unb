import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Farmacia } from 'app/shared/model/farmacia.model';
import { FarmaciaService } from './farmacia.service';
import { FarmaciaComponent } from './farmacia.component';
import { FarmaciaDetailComponent } from './farmacia-detail.component';
import { FarmaciaUpdateComponent } from './farmacia-update.component';
import { FarmaciaDeletePopupComponent } from './farmacia-delete-dialog.component';
import { IFarmacia } from 'app/shared/model/farmacia.model';

@Injectable({ providedIn: 'root' })
export class FarmaciaResolve implements Resolve<IFarmacia> {
  constructor(private service: FarmaciaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFarmacia> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Farmacia>) => response.ok),
        map((farmacia: HttpResponse<Farmacia>) => farmacia.body)
      );
    }
    return of(new Farmacia());
  }
}

export const farmaciaRoute: Routes = [
  {
    path: '',
    component: FarmaciaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Farmacias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FarmaciaDetailComponent,
    resolve: {
      farmacia: FarmaciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Farmacias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FarmaciaUpdateComponent,
    resolve: {
      farmacia: FarmaciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Farmacias'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FarmaciaUpdateComponent,
    resolve: {
      farmacia: FarmaciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Farmacias'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const farmaciaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FarmaciaDeletePopupComponent,
    resolve: {
      farmacia: FarmaciaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Farmacias'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
