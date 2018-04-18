import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';

//Models
import { Cluster } from '../shared/cluster/cluster.model';
import { JobExample } from './health/job-example.model';
import { HdfsHealthReport } from './health/hdfs/hdfs-health-report.model';
import { ClusterSnapshot } from './cluster-snapshot.model';
//Services
import { ClusterService } from './cluster.service';
import { ClusterHealthCheckService } from './health/cluster-health-check.service';
import { RouteService } from '../shared/menu/side/route.service';

@Component({
  selector: 'cluster-info',
  templateUrl: './cluster.component.html',
})
export class ClusterComponent implements OnInit, OnDestroy {
  yarnAppsCount: number;
  //For Inputs should use complex types
  private _clusterName: String;
  //Reports
  private _hdfsHealthReport: HdfsHealthReport;
  private _yarnHealthReport: HdfsHealthReport;
  //Sub notification chanel
  private _sub: Subscription;

  constructor( private router: Router, private route: ActivatedRoute,
    private clusterHealthCheckService: ClusterHealthCheckService, private routeService: RouteService ) {
      this._sub = routeService.healthCheckMessage$.subscribe(
        clusterName => this.clusterName = new String( clusterName )
      );
  }

  ngOnInit() {
    this.route.paramMap.subscribe( (params: ParamMap) => {
      this.clusterName = new String( params.get( 'id' ) );
    });
  }

  ngOnDestroy() {
    this._sub.unsubscribe();
  }

  onYarnAppsCountChange( newYarnAppsCount: number ) {
    this.yarnAppsCount = newYarnAppsCount;
  }

  set clusterName( clusterName: String ) {
    if ( clusterName ) {
      this._clusterName = clusterName;
      this.ascForHdfsAndYarnReports();
    }
  }

  get clusterName(): String {
    return this._clusterName;
  }

  get hdfsHealthReport(): HdfsHealthReport {
    return this._hdfsHealthReport;
  }

  get yarnHealthReport(): HdfsHealthReport {
    return this._yarnHealthReport;
  }

  private ascForHdfsAndYarnReports() {
    this.clusterHealthCheckService.getHdfsClusterState( this._clusterName.toString() ).subscribe(
      data => this._hdfsHealthReport = data
    );
    //Disabled for development
    // this.clusterHealthCheckService.getYarnClusterState( this._clusterName ).subscribe(
    //   data => this._yarnHealthReport = data
    // )
  }
}
