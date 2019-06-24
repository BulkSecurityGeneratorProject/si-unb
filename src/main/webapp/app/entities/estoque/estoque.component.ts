import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEstoque } from 'app/shared/model/estoque.model';
import { AccountService } from 'app/core';
import { EstoqueService } from './estoque.service';

@Component({
  selector: 'jhi-estoque',
  templateUrl: './estoque.component.html'
})
export class EstoqueComponent implements OnInit, OnDestroy {
  estoques: IEstoque[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected estoqueService: EstoqueService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.estoqueService
      .query()
      .pipe(
        filter((res: HttpResponse<IEstoque[]>) => res.ok),
        map((res: HttpResponse<IEstoque[]>) => res.body)
      )
      .subscribe(
        (res: IEstoque[]) => {
          this.estoques = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInEstoques();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEstoque) {
    return item.id;
  }

  registerChangeInEstoques() {
    this.eventSubscriber = this.eventManager.subscribe('estoqueListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
