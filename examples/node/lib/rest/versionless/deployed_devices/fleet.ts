/*
 * This code was generated by
 * ___ _ _ _ _ _    _ ____    ____ ____ _    ____ ____ _  _ ____ ____ ____ ___ __   __
 *  |  | | | | |    | |  | __ |  | |__| | __ | __ |___ |\ | |___ |__/ |__|  | |  | |__/
 *  |  |_|_| | |___ | |__|    |__| |  | |    |__] |___ | \| |___ |  \ |  |  | |__| |  \
 *
 * Twilio - Versionless
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * NOTE: This class is auto generated by OpenAPI Generator.
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { inspect, InspectOptions } from "util";
import DeployedDevices from "../DeployedDevices";
const deserialize = require("../../../base/deserialize");
const serialize = require("../../../base/serialize");
import { isValidPathParam } from "../../../base/utility";

/**
 * Options to pass to create a FleetInstance
 *
 * @property { string } [name]
 */
export interface FleetListInstanceCreateOptions {
  name?: string;
}

export interface FleetContext {
  /**
   * Fetch a FleetInstance
   *
   * @param { function } [callback] - Callback to handle processed record
   *
   * @returns { Promise } Resolves to processed FleetInstance
   */
  fetch(
    callback?: (error: Error | null, item?: FleetInstance) => any
  ): Promise<FleetInstance>;

  /**
   * Provide a user-friendly representation
   */
  toJSON(): any;
  [inspect.custom](_depth: any, options: InspectOptions): any;
}

export interface FleetContextSolution {
  sid?: string;
}

export class FleetContextImpl implements FleetContext {
  protected _solution: FleetContextSolution;
  protected _uri: string;

  constructor(protected _version: DeployedDevices, sid: string) {
    if (!isValidPathParam(sid)) {
      throw new Error("Parameter 'sid' is not valid.");
    }

    this._solution = { sid };
    this._uri = `/Fleets/${sid}`;
  }

  fetch(callback?: any): Promise<FleetInstance> {
    let operationVersion = this._version,
      operationPromise = operationVersion.fetch({
        uri: this._uri,
        method: "get",
      });

    operationPromise = operationPromise.then(
      (payload) =>
        new FleetInstance(operationVersion, payload, this._solution.sid)
    );

    operationPromise = this._version.setPromiseCallback(
      operationPromise,
      callback
    );
    return operationPromise;
  }

  /**
   * Provide a user-friendly representation
   *
   * @returns Object
   */
  toJSON() {
    return this._solution;
  }

  [inspect.custom](_depth: any, options: InspectOptions) {
    return inspect(this.toJSON(), options);
  }
}

interface FleetPayload extends FleetResource {}

interface FleetResource {
  name?: string;
  sid?: string | null;
  friendly_name?: string | null;
}

export class FleetInstance {
  protected _solution: FleetContextSolution;
  protected _context?: FleetContext;

  constructor(
    protected _version: DeployedDevices,
    payload: FleetResource,
    sid?: string
  ) {
    this.name = payload.name;
    this.sid = payload.sid;
    this.friendlyName = payload.friendly_name;

    this._solution = { sid: sid || this.sid };
  }

  name?: string;
  /**
   * A string that uniquely identifies this Fleet.
   */
  sid?: string | null;
  /**
   * A human readable description for this Fleet.
   */
  friendlyName?: string | null;

  private get _proxy(): FleetContext {
    this._context =
      this._context || new FleetContextImpl(this._version, this._solution.sid);
    return this._context;
  }

  /**
   * Fetch a FleetInstance
   *
   * @param { function } [callback] - Callback to handle processed record
   *
   * @returns { Promise } Resolves to processed FleetInstance
   */
  fetch(
    callback?: (error: Error | null, item?: FleetInstance) => any
  ): Promise<FleetInstance> {
    return this._proxy.fetch(callback);
  }

  /**
   * Provide a user-friendly representation
   *
   * @returns Object
   */
  toJSON() {
    return {
      name: this.name,
      sid: this.sid,
      friendlyName: this.friendlyName,
    };
  }

  [inspect.custom](_depth: any, options: InspectOptions) {
    return inspect(this.toJSON(), options);
  }
}

export interface FleetListInstance {
  (sid: string): FleetContext;
  get(sid: string): FleetContext;

  /**
   * Create a FleetInstance
   *
   * @param { function } [callback] - Callback to handle processed record
   *
   * @returns { Promise } Resolves to processed FleetInstance
   */
  create(
    callback?: (error: Error | null, item?: FleetInstance) => any
  ): Promise<FleetInstance>;
  /**
   * Create a FleetInstance
   *
   * @param { FleetListInstanceCreateOptions } params - Parameter for request
   * @param { function } [callback] - Callback to handle processed record
   *
   * @returns { Promise } Resolves to processed FleetInstance
   */
  create(
    params: FleetListInstanceCreateOptions,
    callback?: (error: Error | null, item?: FleetInstance) => any
  ): Promise<FleetInstance>;
  create(params?: any, callback?: any): Promise<FleetInstance>;

  /**
   * Provide a user-friendly representation
   */
  toJSON(): any;
  [inspect.custom](_depth: any, options: InspectOptions): any;
}

export interface FleetSolution {}

interface FleetListInstanceImpl extends FleetListInstance {}
class FleetListInstanceImpl implements FleetListInstance {
  _version?: DeployedDevices;
  _solution?: FleetSolution;
  _uri?: string;
}

export function FleetListInstance(version: DeployedDevices): FleetListInstance {
  const instance = ((sid) => instance.get(sid)) as FleetListInstanceImpl;

  instance.get = function get(sid): FleetContext {
    return new FleetContextImpl(version, sid);
  };

  instance._version = version;
  instance._solution = {};
  instance._uri = `/Fleets`;

  instance.create = function create(
    params?: any,
    callback?: any
  ): Promise<FleetInstance> {
    if (typeof params === "function") {
      callback = params;
      params = {};
    } else {
      params = params || {};
    }

    let data: any = {};

    if (params["name"] !== undefined) data["Name"] = params["name"];

    const headers: any = {};
    headers["Content-Type"] = "application/x-www-form-urlencoded";

    let operationVersion = version,
      operationPromise = operationVersion.create({
        uri: this._uri,
        method: "post",
        data,
        headers,
      });

    operationPromise = operationPromise.then(
      (payload) => new FleetInstance(operationVersion, payload)
    );

    operationPromise = this._version.setPromiseCallback(
      operationPromise,
      callback
    );
    return operationPromise;
  };

  instance.toJSON = function toJSON() {
    return this._solution;
  };

  instance[inspect.custom] = function inspectImpl(
    _depth: any,
    options: InspectOptions
  ) {
    return inspect(this.toJSON(), options);
  };

  return instance;
}
