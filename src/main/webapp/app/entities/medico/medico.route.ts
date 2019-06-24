import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Medico } from 'app/shared/model/medico.model';
import { MedicoService } from './medico.service';
import { MedicoComponent } from './medico.component';
import { MedicoDetailComponent } from './medico-detail.component';
import { MedicoUpdateComponent } from './medico-update.component';
import { MedicoDeletePopupComponent } from './medico-delete-dialog.component';
import { IMedico } from 'app/shared/model/medico.model';

@Injectable({ providedIn: 'root' })
export class MedicoResolve implements Resolve<IMedico> {
  constructor(private service: MedicoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMedico> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Medico>) => response.ok),
        map((medico: HttpResponse<Medico>) => medico.body)
      );
    }
    return of(new Medico());
  }
}

export const medicoRoute: Routes = [
  {
    path: '',
    component: MedicoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Medicos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MedicoDetailComponent,
    resolve: {
      medico: MedicoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Medicos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MedicoUpdateComponent,
    resolve: {
      medico: MedicoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Medicos'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MedicoUpdateComponent,
    resolve: {
      medico: MedicoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Medicos'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const medicoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MedicoDeletePopupComponent,
    resolve: {
      medico: MedicoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Medicos'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
