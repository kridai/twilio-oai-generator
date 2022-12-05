<?php

/*
 * This code was generated by
 * ___ _ _ _ _ _    _ ____    ____ ____ _    ____ ____ _  _ ____ ____ ____ ___ __   __
 *  |  | | | | |    | |  | __ |  | |__| | __ | __ |___ |\ | |___ |__/ |__|  | |  | |__/
 *  |  |_|_| | |___ | |__|    |__| |  | |    |__] |___ | \| |___ |  \ |  |  | |__| |  \
 *
 * Twilio - Accounts
 * This is the public Twilio REST API.
 *
 * NOTE: This class is auto generated by OpenAPI Generator.
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


namespace Twilio\Rest\Api\V2010\Account\Call;

use Twilio\Exceptions\TwilioException;
use Twilio\ListResource;
use Twilio\Options;
use Twilio\Stream;
use Twilio\Values;
use Twilio\Version;
use Twilio\InstanceContext;
use Twilio\Deserialize;
use Twilio\Serialize;



class FeedbackCallSummaryContext extends InstanceContext {

    /**
     * Initialize the FeedbackCallSummaryContext
     *
     * @param Version $version Version that contains the resource
     * @param string $accountSid 
     * @param string $sid 
     */
    public function __construct(Version $version, $accountSid , $sid ) {
        parent::__construct($version);

        // Path Solution
        $this->solution = ['accountSid' => $accountSid,  'sid' => $sid,  ];

        $this->uri = '/Accounts/' . \rawurlencode($accountSid) . '/Calls/Feedback/Summary/' . \rawurlencode($sid) . '.json';
    }

    /**
    * Update the FeedbackCallSummaryInstance
    *
    * @param \DateTime $endDate 
    * @param \DateTime $startDate 
    * @param array|Options $options Optional Arguments
    * @return FeedbackCallSummaryInstance Updated FeedbackCallSummaryInstance
    * @throws TwilioException When an HTTP error occurs.
    */
    public function update(\DateTime $endDate, \DateTime $startDate, array $options = []): FeedbackCallSummaryInstance {
        $options = new Values($options);

        $data = Values::of([
            'EndDate' => Serialize::iso8601Date($endDate),
            'StartDate' => Serialize::iso8601Date($startDate),
            'AccountSid' => $options['accountSid'],
        ]);

        $payload = $this->version->update('POST', $this->uri, [], $data);

        return new FeedbackCallSummaryInstance($this->version, $payload, $this->solution['accountSid'], $this->solution['sid']);
    }

    /**
     * Provide a friendly representation
     *
     * @return string Machine friendly representation
     */
    public function __toString(): string {
        $context = [];
        foreach ($this->solution as $key => $value) {
            $context[] = "$key=$value";
        }
        return '[Twilio.Api.V2010.FeedbackCallSummaryContext ' . \implode(' ', $context) . ']';
    }
}
