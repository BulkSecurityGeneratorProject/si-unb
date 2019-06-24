import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Estoque } from 'app/shared/model/estoque.model';
import { EstoqueService } from './estoque.service';
import { EstoqueComponent } from './estoque.component';
import { EstoqueDetailComponent } from './estoque-detail.component';
import { EstoqueUpdateComponent } from './estoque-update.component';
import { EstoqueDeletePopupComponent } from './estoque-delete-dialog.component';
import { IEstoque } from 'app/shared/model/estoque.model';

@Injectable({ providedIn: 'root' })
export class EstoqueResolve implements Resolve<IEstoque> {
  constructor(private service: EstoqueService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IEstoque> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Estoque>) => response.ok),
        map((estoque: HttpResponse<Estoque>) => estoque.body)
      );
    }
    return of(new Estoque());
  }
}

export const estoqueRoute: Routes = [
  {
    path: '',
    component: EstoqueComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Estoques'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EstoqueDetailComponent,
    resolve: {
      estoque: EstoqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Estoques'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EstoqueUpdateComponent,
    resolve: {
      estoque: EstoqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Estoques'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EstoqueUpdateComponent,
    resolve: {
      estoque: EstoqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Estoques'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const estoquePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: EstoqueDeletePopupComponent,
    resolve: {
      estoque: EstoqueResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Estoques'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
