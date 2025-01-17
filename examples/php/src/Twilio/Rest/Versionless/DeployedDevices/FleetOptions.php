<?php
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

namespace Twilio\Rest\Versionless\DeployedDevices;

use Twilio\Exceptions\TwilioException;
use Twilio\ListResource;
use Twilio\InstanceResource;
use Twilio\Options;
use Twilio\Stream;
use Twilio\Values;
use Twilio\Version;
use Twilio\InstanceContext;
use Twilio\Deserialize;
use Twilio\Serialize;


abstract class FleetOptions {
    /**
    * @param string $name  
    * @return CreateFleetOptions Options builder
    */
    public static function create(string  $name=Values::NONE): CreateFleetOptions {
        return new CreateFleetOptions($name);
    }


}

class CreateFleetOptions extends Options {
    /**
    * @param string $name 
    */
    public function __construct(string  $name=Values::NONE) {
        $this->options['name'] = $name;
    }

    /**
    * @param string $name 
    * @return $this Fluent Builder
    */
    public function setName(string $name): self {
        $this->options['name'] = $name;
        return $this;
    }

    /**
    * Provide a friendly representation
    *
    * @return string Machine friendly representation
    */
    public function __toString(): string {
        $options = \http_build_query(Values::of($this->options), '', ' ');
        return '[Twilio.Versionless.DeployedDevices.CreateFleetOptions ' . $options . ']';
    }
}


