"""
    This code was generated by
    ___ _ _ _ _ _    _ ____    ____ ____ _    ____ ____ _  _ ____ ____ ____ ___ __   __
    |  | | | | |    | |  | __ |  | |__| | __ | __ |___ |\ | |___ |__/ |__|  | |  | |__/
    |  |_|_| | |___ | |__|    |__| |  | |    |__] |___ | \| |___ |  \ |  |  | |__| |  \

    Twilio - Accounts
    This is the public Twilio REST API.

    NOTE: This class is auto generated by OpenAPI Generator.
    https://openapi-generator.tech
    Do not edit the class manually.
"""


from twilio.base import deserialize
from twilio.base import serialize
from twilio.base import values
from twilio.base.instance_context import InstanceContext
from twilio.base.instance_resource import InstanceResource
from twilio.base.list_resource import ListResource

from twilio.base.page import Page

from twilio.rest.aws.history import HistoryListInstance


class AwsContext(InstanceContext):
    def __init__(self, version: V1, sid: str):
        # TODO: needs autogenerated docs
        super(AwsContextList, self).__init__(version)

        # Path Solution
        self._solution = { sid,  }
        self._uri = '/Credentials/AWS/${sid}'
        
        self._history = None
        
        def delete(self):
            
            
            """
            Deletes the AwsInstance

            :returns: True if delete succeeds, False otherwise
            :rtype: bool
            """
            return self._version.delete(method='DELETE', uri=self._uri, )
        
        def fetch(self):
            
            """
            Fetch the AwsInstance

            :returns: The fetched AwsInstance
            #TODO: add rtype docs
            """
            payload = self._version.fetch(method='GET', uri=self._uri, )
            return AwsInstance(
                self._version,
                payload,
                sid=self._solution['sid'],
            )
            
            
        
        def update(self, body):
            data = values.of({
                'body': body,
            })

            payload = self._version.update(method='post', uri=self._uri, data=data, )

            return AwsInstance(self._version, payload, sid=self._solution['sid'], )
            
            
        

    def __repr__(self):
        """
        Provide a friendly representation
        :returns: Machine friendly representation
        :rtype: str
        """
        return '<Twilio.Api.V1.AwsContext>'



class AwsInstance(InstanceResource):
    def __init__(self, version, payload, sid: str):
        super(AwsInstance, self).__init__(version)
        self._properties = { 
            'account_sid' = payload.get('account_sid'),
            'sid' = payload.get('sid'),
            'test_string' = payload.get('test_string'),
            'test_integer' = payload.get('test_integer'),
        }

        self._context = None
        self._solution = {
            'sid': sid or self._properties['sid']
        }

    @property
    def _proxy(self):
        if self._context is None:
            self._context = AwsContext(
                self._version,
                sid=self._solution['sid'],
            )
        return self._context

    @property
    def history(self):
        return self._proxy.history
    

    def __repr__(self):
        """
        Provide a friendly representation
        :returns: Machine friendly representation
        :rtype: str
        """
        context = ' '.join('{}={}'.format(k, v) for k, v in self._solution.items())
        return '<Twilio.Api.V1.AwsInstance {}>'.format(context)



class AwsListInstance(ListResource):
    def __init__(self, version: V1):
        # TODO: needs autogenerated docs
        super(AwsListInstanceList, self).__init__(version)

        # Path Solution
        self._solution = {  }
        self._uri = '/Credentials/AWS'
        
        
        def page(self, page_size):
            
            data = values.of({
                'page_size': page_size,
            })

            payload = self._version.create(method='get', uri=self._uri, data=data, )

            return AwsPage(self._version, payload, )
        

    def __repr__(self):
        """
        Provide a friendly representation
        :returns: Machine friendly representation
        :rtype: str
        """
        return '<Twilio.Api.V1.AwsListInstance>'

